package no.mesan.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds specific tags in HTML string. Not designed for effectiveness.
 *
 * @author Mikkel Steine
 */
public class TagFinder {

    private static final String STARTTAG = "<";
    private static final String ENDTAG = ">";
    private final List<String> tags;

    public TagFinder() {
        tags = new ArrayList<>();
    }

    /**
     * Finds tags of the given name in the supplied HTML code.
     * @param tagName Name of tag
     * @param html HTML content
     * @return Number of tags found.
     */
    public int findTagsInHTML(final String tagName, final String html) {
        final String tagStart = STARTTAG + tagName + " ";
        int nextPosition = 0;
        final int tagLength = tagStart.length();
        final int fullLength = html.length();
        int count = 0;
        tags.clear();

        while (nextPosition >= 0 && nextPosition < fullLength) {
            final int foundPosition = html.indexOf(tagStart, nextPosition);
            if (foundPosition >= 0) {
                final int endTagPosition = html.indexOf(ENDTAG, foundPosition);
                if (endTagPosition > 0) {
                    tags.add(html.substring(foundPosition, endTagPosition+1));
                    count++;
                    nextPosition = endTagPosition;
                } else {
                    nextPosition += tagLength;
                }
            } else {
                nextPosition = foundPosition;
            }
        }
        return count;
    }

    /**
     * Return all tags found.
     * @return List of opening tags that where found.
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Get a list of the content of the given attribute for all found tags. Only found attributes will be added to
     * the list.
     * @param attributeName Name of the attribute to add.
     * @return List of attribute values.
     */
    public List<String> getAttributeInTags(final String attributeName) {
        final List<String> attributes = new ArrayList<>(tags.size());
        final String pattern = attributeName + "=\"([^\"]+)\"";
        final Pattern regexp = Pattern.compile(pattern);
        for (final String tag : tags) {
            final Matcher m = regexp.matcher(tag);
            if (m.find()) {
                attributes.add(m.group(1));
            }
        }
        return attributes;
    }
}

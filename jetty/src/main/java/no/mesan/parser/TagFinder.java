package no.mesan.parser;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getTags() {
        return tags;
    }

/*    private String getAHref(final String tag) {

    }*/
}

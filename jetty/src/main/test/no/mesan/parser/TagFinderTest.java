package no.mesan.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TagFinderTest {

    public final static String HTML =
            "<body><a href=\"/x.html\">1</a><a href=\"/y.html\">2</a><img src=\"p.jpg\"/></body>";
    public final TagFinder tagFinder = new TagFinder();

    @Test
    public void shouldFindCorrectNumberOfTags() {
        Assert.assertEquals(2, tagFinder.findTagsInHTML("a", HTML));
        Assert.assertEquals(2, tagFinder.getTags().size());

        Assert.assertEquals(1, tagFinder.findTagsInHTML("img", HTML));
        Assert.assertEquals(1, tagFinder.getTags().size());
    }

    @Test
    public void shouldGetCorrectTags() {
        tagFinder.findTagsInHTML("a", HTML);
        List<String> tags = tagFinder.getTags();
        Assert.assertEquals("<a href=\"/x.html\">", tags.get(0));
        Assert.assertEquals("<a href=\"/y.html\">", tags.get(1));

        tagFinder.findTagsInHTML("img", HTML);
        tags = tagFinder.getTags();
        Assert.assertEquals("<img src=\"p.jpg\"/>", tags.get(0));
    }

    @Test
    public void shouldGetCorrectAttributes() {
        tagFinder.findTagsInHTML("a", HTML);
        final List<String> hrefs = tagFinder.getAttributeInTags("href");
        Assert.assertEquals(2, hrefs.size());
        Assert.assertEquals("/x.html", hrefs.get(0));
        Assert.assertEquals("/y.html", hrefs.get(1));
    }
}

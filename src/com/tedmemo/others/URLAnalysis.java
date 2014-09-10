package com.tedmemo.others;

import com.tedmemo.data.IncludedUrlInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ted on 2014/9/10.
 */
public class URLAnalysis {

    private static final Pattern pattern = Pattern.compile("(http|https):([^\\x00-\\x20()\"<>\\x7F-\\xFF])*", Pattern.CASE_INSENSITIVE);

    public static List<IncludedUrlInfo> getUrl(String paramString) {
        ArrayList localArrayList = new ArrayList();
        Matcher localMatcher = pattern.matcher(paramString);
        while (localMatcher.find()) {
            IncludedUrlInfo localIncludedUrlInfo = new IncludedUrlInfo();
            localIncludedUrlInfo.setUrl(localMatcher.group());
            localIncludedUrlInfo.setStartPosition(localMatcher.start());
            localIncludedUrlInfo.setEndPosition(localMatcher.end());
            localArrayList.add(localIncludedUrlInfo);
        }
        return localArrayList;
    }
}

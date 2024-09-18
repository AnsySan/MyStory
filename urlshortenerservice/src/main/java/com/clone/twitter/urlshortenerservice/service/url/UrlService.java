package com.clone.twitter.urlshortenerservice.service.url;

import com.clone.twitter.urlshortenerservice.dto.UrlDto;

public interface UrlService {

    public String redirectOriginalUrl(String hash);

    public String convertToShortUrl(UrlDto urlDto);

}

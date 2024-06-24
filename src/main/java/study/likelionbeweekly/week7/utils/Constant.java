package study.likelionbeweekly.week7.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Constant {

    AUTHORIZATION_HEADER_KEY("Authorization");

    private final String content;
}

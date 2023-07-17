package Alchole_free.Cockpybara.controller.member.update;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static Alchole_free.Cockpybara.constant.RegexConstant.ALIAS_REGEX;
import static Alchole_free.Cockpybara.constant.RegexConstant.PHONE_NUMBER_REGEX;

@Data
public class MemberInfoUpdateRequest {


    @NotNull
    @Pattern(regexp = ALIAS_REGEX, message = "잘못된 별명 형식입니다.")
    private String alias;

    @NotNull
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "잘못된 번호 형식입니다.")
    private String phoneNumber;

}

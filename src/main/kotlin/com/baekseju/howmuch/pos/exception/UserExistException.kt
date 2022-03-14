package com.baekseju.howmuch.pos.exception

class UserExistException(
    phoneNumber: String
) : RuntimeException("입력하신 ${phoneNumber}는 이미 가입된 번호입니다.") {
}

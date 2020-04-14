package me.sun.arduino;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member")
    public Member findMember(@RequestParam String name){
        Member member = memberRepository.findByName(name).get();
        System.out.println(member);
        return member;
    }


}

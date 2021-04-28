package cells.controller;

import cells.model.Member;
import cells.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllStudents(){
        List<Member> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id){
        Member member = memberService.getMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Member> addMember(@RequestBody Member member){
        Member newMember = memberService.addMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Member> updateMember(@RequestBody Member member){
        Member updateMember = memberService.updateMember(member);
        return new ResponseEntity<>(updateMember, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id){
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


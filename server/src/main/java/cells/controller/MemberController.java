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
    public ResponseEntity<List<Member>> getAll(){
        // added Member member so that caching will be possible
        List<Member> members = memberService.getAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Member> getById(@PathVariable("id") Long id){
        Member member = memberService.getById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Member> add(@RequestBody Member member){
        Member newMember = memberService.add(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Member> update(@RequestBody Member member){
        Member updateMember = memberService.update(member);
        return new ResponseEntity<>(updateMember, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


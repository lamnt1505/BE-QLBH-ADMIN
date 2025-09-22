package com.vnpt.mini_project_java.restcontroller;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam String sender, @RequestParam String content) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("chat/conversations");

            Map<String, Object> message = new HashMap<>();
            message.put("sender", sender);
            message.put("content", content);
            message.put("timestamp", System.currentTimeMillis());

            ref.push().setValueAsync(message);
            return ResponseEntity.ok("Đã gửi thành công");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Send failed");
        }
    }
}

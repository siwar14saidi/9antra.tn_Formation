/*package config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();
        String username = user.getUsername();

        if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("apprenant"))) {
            return ResponseEntity.ok().body("Bonjour, apprenant " + username);
        } else if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("formateur"))) {
            return ResponseEntity.ok().body("Bonjour, formateur " + username);
        } else if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("manager"))) {
            return ResponseEntity.ok().body("Bonjour, manager " + username);
        } else if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("admin"))) {
            return ResponseEntity.ok().body("Bonjour, admin " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non autorisé");
        }
    }
}
*/
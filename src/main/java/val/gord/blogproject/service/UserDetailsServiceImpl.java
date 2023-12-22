package val.gord.blogproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import val.gord.blogproject.dto.SignUpRequestDto;
import val.gord.blogproject.dto.UserResponseDto;
import val.gord.blogproject.error.BadRequestException;
import val.gord.blogproject.error.BlogException;
import val.gord.blogproject.repository.RoleRepository;
import val.gord.blogproject.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    //props:
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signUp(SignUpRequestDto dto){
        //1) get the user role from role repo
        var  userRole = roleRepository.findByNameIgnoreCase("ROLE_USER").orElseThrow(()->new BlogException("Please contact to admin"));

        //2) if email/username  exists -> Go sign in (Exception)
        var  byUser = userRepository.findByUsernameIgnoreCase(dto.getUsername().trim());
        var  byEmail = userRepository.findByEmailIgnoreCase(dto.getEmail().trim());
        if (byEmail.isPresent()){
            throw new BadRequestException("email","Email  already exist");
        } else if (byUser.isPresent()) {
            throw new BadRequestException("username","Username  already exist");
        }

        //3) val user = new User(...encoded-password
        var user = new val.gord.blogproject.entity.User(
                null,
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword().trim()),
                List.of(),
                Set.of(userRole)
        );

        //4) save
        var savedUser = userRepository.save(user);
        //5) response dto
        return modelMapper.map(savedUser, UserResponseDto.class);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch our user entity from our database
        var user = userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        //map our roles to Springs SimpleGrantedAuthority:
        var roles = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();

        //return new org.springframework.security.core.userdetails.User
        //spring User implements UserDetails
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),roles);

    }
}

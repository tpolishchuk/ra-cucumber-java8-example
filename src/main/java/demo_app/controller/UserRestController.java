package demo_app.controller;

import demo_app.domain.User;
import demo_app.domain.validation.OnCreate;
import demo_app.domain.validation.OnUpdate;
import demo_app.exception.ValidationException;
import demo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@RestController
@RequestMapping("api/v1/")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Value("${pages.default.start.page.number}")
    private int pageNumber;

    @Value("${pages.default.page.size}")
    private int pageSize;

    @GetMapping("users")
    public List<User> list(@RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> pageNumber = p);
        size.ifPresent(s -> pageSize = s);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return userService.findAllPaginated(pageRequest).getContent();
    }

    @PostMapping("users")
    public User create(@Validated(OnCreate.class) @RequestBody User user,
                       BindingResult bindingResult) {
        try {
            getPrettyBindingResult(bindingResult);

            return userService.add(user);
        } catch (ConstraintViolationException eValidation) {
            checkConstraintViolations(new ArrayList<>(eValidation.getConstraintViolations()));
        }
        throw new IllegalStateException("Unable to create user " + user.toString());
    }

    @GetMapping("/users/{id}")
    public User get(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PutMapping("/users/{id}")
    public User update(@PathVariable Integer id, @Validated(OnUpdate.class) @RequestBody User user,
                       BindingResult bindingResult) {
        try {
            getPrettyBindingResult(bindingResult);

            return userService.update(id, user);
        } catch (ConstraintViolationException eValidation) {
            checkConstraintViolations(new ArrayList<>(eValidation.getConstraintViolations()));
        }
        throw new IllegalStateException("Unable to update user with id " + id);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Integer id) {
        userService.deleteById(id);
    }

    private void getPrettyBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder("Message body did not pass validation rules:");

            List<ObjectError> globalErrors = new ArrayList<>(bindingResult.getGlobalErrors());
            Collections.sort(globalErrors, Comparator.comparing((ObjectError::getDefaultMessage)));

            for (ObjectError globalError : globalErrors) {
                stringBuilder.append(" {").append(globalError.getObjectName())
                             .append(": ").append(globalError.getDefaultMessage()).append("}");
            }

            List<FieldError> fieldErrors = new ArrayList<>(bindingResult.getFieldErrors());
            Collections.sort(fieldErrors, Comparator.comparing((FieldError::getDefaultMessage)));

            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(" {").append(fieldError.getObjectName())
                             .append(": ").append(fieldError.getDefaultMessage()).append("}");
            }

            throw new ValidationException(stringBuilder.toString());
        }
    }

    private void checkConstraintViolations(List<ConstraintViolation> constraintViolations) {
        StringBuilder stringBuilder = new StringBuilder("Message body did not pass validation rules:");
        Collections.sort(constraintViolations, Comparator.comparing(ConstraintViolation::getMessage));

        for (ConstraintViolation constraintViolation : constraintViolations) {
            stringBuilder.append(" {").append(constraintViolation.getPropertyPath().toString())
                         .append(": ").append(constraintViolation.getMessage()).append("}");
        }

        throw new ValidationException(stringBuilder.toString());
    }
}

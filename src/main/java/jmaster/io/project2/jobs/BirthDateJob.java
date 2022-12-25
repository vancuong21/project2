package jmaster.io.project2.jobs;

import jmaster.io.project2.entity.User;
import jmaster.io.project2.repo.UserRepo;
import jmaster.io.project2.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Slf4j // để thế này sẽ tạo sẵn 1 log
public class BirthDateJob {
    @Autowired
    UserRepo userRepo;
    @Autowired
    EmailService emailService;

    // tim user co sinh nhat hom nay, va gui chuc mung
    // gui email chuc mung
    @Scheduled(cron = "0 * * * * *")
    public void sendEmailBirthdate() {
        System.out.println("Init birthdate job");
        // 1. tim user co sinh nhat hom nay
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // +- ngay, gio
        //   cal.add(0, 0);
        Date now = cal.getTime();
        log.info("" + now);

        List<User> users = userRepo.findByBirthdate(cal.get(Calendar.DATE), cal.get(Calendar.MONTH) + 1);

        for (User user : users) {
            log.info(user.getName());
            // send email, gia su username la email (to)
            emailService.sendBirthdate(user.getEmail(), user.getName());
        }

//        // test email
//        emailService.sendTest();
    }

}

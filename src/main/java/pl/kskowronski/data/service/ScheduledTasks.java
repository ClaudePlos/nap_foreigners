package pl.kskowronski.data.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kskowronski.data.entity.inap.NapForeignerLogDTO;
import pl.kskowronski.data.service.inap.NapForeignerLogService;
import pl.kskowronski.data.service.inap.ProcessInstanceService;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledTasks {

    private NapForeignerLogService napForeignerLogService;
    private MailService mailService;
    private ProcessInstanceService processInstanceService;
    private transient Optional<List<NapForeignerLogDTO>> foreigners;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
    public ScheduledTasks(NapForeignerLogService napForeignerLogService, MailService mailService, ProcessInstanceService processInstanceService) {
        this.napForeignerLogService = napForeignerLogService;
        this.mailService = mailService;
        this.processInstanceService = processInstanceService;
    }

    @Scheduled(cron = "0 12 13 * * MON-FRI")
    public void checkSuspendedAndSendEmail() throws Exception {
        System.out.println("TEST / ***************************");
        LocalDate today = LocalDate.now();

        foreigners = napForeignerLogService.findAllSuspendedForPeriod( today.format(formatter) );

        foreigners.get().forEach( f -> {
            String topic = "Obcokrajowcy. Wniosek do poprawy "+ f.getPrcSurname()+ " " + f.getPrcName() + " ProcId: " + f.getProcessId();

            String runProcess = "";
            if (f.getPlatform().equals("suncode")) {
                runProcess = f.getWhoRunInInap();
            } else {
                runProcess = processInstanceService.getProcessInstance(f.getProcessId()).get().getRunProcess();
            }

            String to = "";
            if (f.getPlatform().equals("suncode")) {
                to = runProcess;
            } else {
                to = runProcess + "@rekeep.pl";
            }

            sendMailTo(to
                    ,null
                    , f.getDescription()
                    , topic );
        });

    }

    private void sendMailTo(String to, String cc, String text, String topic){
        try {
            mailService.sendMail(to, cc,
                    topic ,
                    "<b>Wiadomość od: " + cc + "</b><br><br> "+ text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

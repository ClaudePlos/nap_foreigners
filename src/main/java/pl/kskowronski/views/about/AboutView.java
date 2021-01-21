package pl.kskowronski.views.about;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kskowronski.data.service.MailService;
import pl.kskowronski.views.main.MainView;

import javax.mail.MessagingException;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("./styles/views/about/about-view.css")
public class AboutView extends Div {
    private MailService mailService;

    public AboutView( @Autowired MailService mailService ) {
        this.mailService = mailService;
        setId("about-view");
        add(new Label("Content made by k.skowronski"));
        Button butTest = new Button("Mail", e -> {
            try {
                mailService.sendMailTest("Test","Co tam u was?", true);
            } catch (MessagingException messagingException) {
                messagingException.printStackTrace();
            }
        });
        //add(butTest);

    }

}

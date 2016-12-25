package com.radioteria.test.business.services.remindPasswordService;

import com.radioteria.backing.template.TemplateService;
import com.radioteria.backing.template.TestTemplateService;
import com.radioteria.business.services.user.api.RemindPasswordService;
import com.radioteria.business.services.user.impl.RemindPasswordServiceImpl;
import com.radioteria.data.dao.api.UserDao;
import com.radioteria.backing.mail.EmailService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.StringJoiner;

@RunWith(MockitoJUnitRunner.class)
abstract public class AbstractRemindPasswordServiceTest {

    @Mock
    protected UserDao userDao;

    @Mock
    protected EmailService emailService;

    protected RemindPasswordService remindPasswordService;

    @Before
    public void setup() {

        remindPasswordService = new RemindPasswordServiceImpl(userDao, emailService);

    }

}

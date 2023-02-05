package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A사용자가 10000원 주문
        statefulService1.order("userA", 10000);

        // ThreadB: B사용자가 20000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA: A사용자가 주문 금액 조회
        int price = statefulService1.getPrice();
        // ThreadA: B사용자가 주문 금액 조회
        int price2 = statefulService2.getPrice();

        System.out.println(price);
        System.out.println(price2);

        assertThat(statefulService1).isSameAs(statefulService2);
        //기대 값은 1만원인데 2만원이 나옴 (왜냐하면 사용자 B가 바꿔버림- 인스턴스는 같아서)
        assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    @Test
    void statelessServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A사용자가 10000원 주문
        int userAPrice = statefulService1.statelessOrder("userA", 10000);

        // ThreadB: B사용자가 20000원 주문
        int userBPrice = statefulService2.statelessOrder("userB", 20000);

        assertThat(userAPrice).isEqualTo(10000);
    }


    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}

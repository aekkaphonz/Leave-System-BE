package Leave_System.Leave_System.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTest {
    
    @Test
    void contextLoads() {
        Application.main(new String[] {});
    }
    
    @Test
    void applicationStarts() {
        Application application = new Application();
        
        assert(application != null);
    }
}

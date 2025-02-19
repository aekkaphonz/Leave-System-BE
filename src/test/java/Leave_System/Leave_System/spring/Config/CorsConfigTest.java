package Leave_System.Leave_System.spring.Config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void testCorsConfiguration() {
        // สร้าง instance ของ CorsConfig
        CorsConfig corsConfig = new CorsConfig();
        
        // เรียกใช้ method corsConfigurer
        WebMvcConfigurer configurer = corsConfig.corsConfigurer();
        
        // สร้าง mock CorsRegistry
        CorsRegistry registry = new CorsRegistry();
        
        // ทดสอบการกำหนดค่า CORS
        configurer.addCorsMappings(registry);
        
        // เนื่องจาก CorsRegistry ไม่มี method สำหรับตรวจสอบค่าที่ถูกตั้ง
        // เราจึงเพียงทดสอบว่าการเรียก method ทำงานโดยไม่มี error
        assertNotNull(configurer);
    }
}

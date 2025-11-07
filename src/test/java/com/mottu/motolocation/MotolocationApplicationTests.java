package com.mottu.motolocation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.mottu.motolocation.repository.MotoRepository;
import com.mottu.motolocation.repository.SensorRepository;
import com.mottu.motolocation.repository.MovimentacaoRepository;
import com.mottu.motolocation.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class MotolocationApplicationTests {

	@MockBean
	private MotoRepository motoRepository;

	@MockBean
	private SensorRepository sensorRepository;

	@MockBean
	private MovimentacaoRepository movimentacaoRepository;

	@MockBean
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

}

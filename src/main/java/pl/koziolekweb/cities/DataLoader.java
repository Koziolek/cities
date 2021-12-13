package pl.koziolekweb.cities;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import pl.koziolekweb.cities.domain.City;
import pl.koziolekweb.cities.domain.CityRepository;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
class DataLoader implements ApplicationRunner {

	private final CityRepository cityRepository;

	@Value("classpath:cities.csv")
	private Resource dataFile;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		CSVParser csvParser = CSVParser.parse(dataFile.getInputStream(), StandardCharsets.UTF_8,
				CSVFormat.DEFAULT
						.withFirstRecordAsHeader()
						.withIgnoreHeaderCase()
						.withTrim());

		cityRepository.saveAll(
				csvParser.getRecords()
						.stream()
						.map(r -> {
							City city = new City();
							city.setId(Long.parseLong(r.get("id")));
							city.setName(r.get("name"));
							city.setPhoto(r.get("photo"));
							return city;
						}).toList());
	}
}

package com.terra.task.cpu.repository;

import com.terra.task.cpu.config.CommonConfig;
import com.terra.task.cpu.domain.CpuUsage;
import com.terra.task.cpu.util.RandomValueGenerator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(CommonConfig.class)
@ActiveProfiles("test")
@DataJpaTest
class CpuUsageRepositoryTest {

  @Autowired
  CpuUsageRepository cpuUsageRepository;

  @Autowired
  Random random;

  @Test
  void 지정한_구간내의_분_단위_CPU_사용률을_조회할_수_있다(){
    // given
    LocalDateTime startDateTime = LocalDateTime.of(2024, 1, 1, 10, 1);
    LocalDateTime endDateTime = LocalDateTime.of(2024, 1, 1, 11, 1);
    insertCpuUsageDataForMinuteRange(startDateTime,  endDateTime);

    // when
    List<CpuUsage> cpuUsageList = cpuUsageRepository.findMinuteCpuUsage(startDateTime, endDateTime);

    // then
    Assertions.assertThat(cpuUsageList).hasSize(60);
  }

  /**
   * 지정한 시간 범위 동안의 CPU 사용량 데이터를 랜덤 값으로 생성하여 저장합니다.
   *
   * @param startDateTime 시작 시간
   * @param endDateTime 종료 시간
   */
  void insertCpuUsageDataForMinuteRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    long minuteBetween = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
    List<CpuUsage> cpuUsageList = new ArrayList<>();

    for(int minute = 0; minute < minuteBetween; minute++) {
      LocalDateTime nextDay = startDateTime.plusMinutes(minute);
      double cpuUsage = RandomValueGenerator.generateRandomValue(random);
      cpuUsageList.add(CpuUsage.of(nextDay, cpuUsage));
    }
    cpuUsageRepository.saveAll(cpuUsageList);
  }

}

package store.streetvendor;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.core.domain.boss.Boss;
import store.streetvendor.core.domain.boss.BossRepository;

@SpringBootTest
public abstract class SetupBoss {

    @Autowired
    public BossRepository bossRepository;

    protected Boss boss = BossFixture.boss();

    @BeforeEach
    void setUpBoss() {
        bossRepository.save(boss);

    }

    protected void cleanup() {
        bossRepository.deleteAll();
    }

}

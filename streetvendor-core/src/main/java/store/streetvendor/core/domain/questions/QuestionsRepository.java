package store.streetvendor.core.domain.questions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Long, Questions> {
}

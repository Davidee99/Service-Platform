package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.model.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

	List<Optional<Chat>> findChatByTicketIdOrderByTimestampAsc(Long ticketId);

	Optional<Chat> findChatByTicketId(Long ticketId);

	@Query(value = "SELECT t.user_id, t.operator_id, t.status FROM ticket t WHERE t.id = :ticketId", nativeQuery = true)
	List<Object[]> findUserAndOperatorByTicketId(Long ticketId);
	
	@Query(value = "SELECT COUNT(*) FROM operators WHERE operator_id = :operatorId", nativeQuery = true)
    int countByOperatorId(Long operatorId);
	
	@Query(value = "SELECT COUNT(*) FROM users WHERE user_id = :userId", nativeQuery = true)
    int countByUserId(Long userId);
	
//	@Query(value = "SELECT COUNT(*) FROM ticket WHERE id = :ticketId AND user_id = :userId AND operator_id = :operatorId AND status = :status", nativeQuery = true)
//    int countTicketsByParams(@Param("ticketId") Long ticketId, @Param("userId") Long userId, @Param("operatorId") Long operatorId, @Param("status") String status);

	@Query(value = "SELECT COUNT(*), STATUS FROM ticket WHERE id = :ticketId AND user_id = :userId AND operator_id = :operatorId GROUP BY status", nativeQuery = true)
    Object[] countTicketsByParamsGroupByStatus(@Param("ticketId") Long ticketId, @Param("userId") Long userId, @Param("operatorId") Long operatorId);
}

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

	// Trova una chat per un dato ID di ticket
	Optional<Chat> findByTicketId(Long ticketId);

	// Ottiene gli ID degli interlocutori (utente e operatore) per un dato ID di
	// ticket
	@Query(value = "SELECT t.user_id, t.operator_id, t.status FROM ticket t WHERE t.id = :ticketId", nativeQuery = true)
	List<Object[]> getInterlocutorsId(Long ticketId);

	// Ottiene lo stato del ticket per un dato ID di ticket, ID di utente e ID di
	// operatore
	// Esegui un controllo forte sui dati per garantire la coerenza e la precisione
	@Query(value = "SELECT COUNT(*), STATUS  FROM ticket WHERE id = :ticketId AND user_id = :userId AND operator_id = :operatorId GROUP BY status", nativeQuery = true)
	List<Object[]> getTicketStatusWithStrongValidation(@Param("ticketId") Long ticketId, @Param("userId") Long userId,
			@Param("operatorId") Long operatorId);

	// Metodo per recuperare l'email del login, il codice di accesso del ticket e
	// l'ID della chat dato un chatId
	@Query(value = "SELECT login.email, ticket.access_code, chat.id AS chat_id " + // Query SQL nativa per recuperare
																					// l'email del login, il codice di
																					// accesso del ticket e l'ID della
																					// chat
			"FROM chat " + // Tabella chat
			"JOIN ticket ON chat.ticket_id = ticket.id " + // Join con la tabella ticket
			"JOIN login_info AS login ON ticket.user_id = login.user_id " + // Join con la tabella login_info
			"WHERE chat.id = :chatId", nativeQuery = true) // Filtro sulla base dell'ID della chat
	List<Object[]> getEmailAccessCodeAndChatIdByChatId(@Param("chatId") Long chatId); // Metodo che accetta l'ID della
																						// chat come parametro e
																						// restituisce una lista di
																						// array di oggetti contenenti
																						// l'email del login, il codice
																						// di accesso del ticket e l'ID
																						// della chat

	@Query(value = "SELECT ID FROM ticket WHERE ACCESS_CODE = :accessCode LIMIT 1", nativeQuery = true)
	Long findTicketIdByAccessCode(@Param("accessCode") String accessCode);
}

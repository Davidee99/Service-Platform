package com.configuration;

/**
 * Lista di url per il dispatcher
 */
public class URLS {

	// MAIL SERVICE
	public static final String SEND_CUSTOM_MAIL = "http://localhost:5000/mailsender/sendCustom/";
	public static final String SEND_DEFAULT_MAIL = "http://localhost:5000/mailsender/sendDefault/";

	// CHAT SERVICE
	public static final String SEND_ATTACHMENT = "http://localhost:8084/api/chat-service/attachment/sendAttachment/";
	public static final String GET_CHAT_BY_TICKET_ID_WITH_ACCESS_CODE = "http://localhost:8084/api/chat-service/chat/getChatByTicketId/withAccessCode/";
	public static final String GET_CHAT_BY_TICKET_ID_NO_ACCESS_CODE = "http://localhost:8084/api/chat-service/chat/getChatByTicketId/noAccessCode/";
	public static final String GET_CHAT_BY_CHAT_ID = "http://localhost:8084/api/chat-service/chat/getChatByChatId/";
	public static final String SEND_MESSAGE = "http://localhost:8084/api/chat-service/message/sendMessage/";

	// TICKET SERVICE
	// - User controller
	public static final String SAVE_TICKET = "http://localhost:8083/api/ticket-service/ticket/saveTicket/";
	public static final String GET_WIP_TICKETS = "http://localhost:8083/api/ticket-service/ticket/getWIPTickets/";
	public static final String GET_NON_WIP_TICKETS = "http://localhost:8083/api/ticket-service/ticket/getNonWIPTickets/";
	// - Operator controller
	public static final String UPDATE_TICKET_STATUS_WIP = "http://localhost:8083/api/ticket-service/operator/update-ticket-status/WIP/";
	public static final String GET_TICKET_WIP_BY_ID = "http://localhost:8083/api/ticket-service/operator/ticketWIP/";
	public static final String GET_TICKET_NON_WIP = "http://localhost:8083/api/ticket-service/operator/ticketNONWIP/";
	public static final String CLOSE_TICKET_OPERATOR = "http://localhost:8083/api/ticket-service/operator/close-ticket/";
	public static final String CHANGE_STATUS_ERROR = "http://localhost:8083/api/ticket-service/operator/change-status-error/";
	// - Admin controller
	public static final String GET_OPEN_AND_MARKED_TICKETS = "http://localhost:8083/api/admin/getOpenAndMarkedTickets/";
	public static final String CLOSE_TICKET_ADMIN = "http://localhost:8083/api/admin/closeTicket/";
	public static final String CHANGE_TICKET_TYPE = "http://localhost:8083/api/admin/changeTicketType/";
	public static final String GET_TICKET_IN_PROGRESS = "http://localhost:8083/api/admin/getTicketsInProgress/";
}

/**
 * 
 */
package com.sevensoftware.domotica.controllers;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.sevensoftware.domotica.controllers.ViviendaController.ThreadDemo;
import com.sevensoftware.domotica.dto.Respuesta;
import com.sevensoftware.domotica.entities.Greeting;
import com.sevensoftware.domotica.entities.HelloMessage;
import com.sevensoftware.domotica.entities.Message;
import com.sevensoftware.domotica.entities.DispositivoItem;
import java.util.List;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * @author LUIS
 *
 */
@Controller
public class DispositivosController {
	
	
	
}

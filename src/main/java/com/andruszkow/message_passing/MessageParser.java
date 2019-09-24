package com.andruszkow.message_passing;

import com.andruszkow.message_passing.data.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MessageParser {
	final private Unmarshaller messageUnmarshaller;

	public MessageParser() {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
			this.messageUnmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException("Exception while initialising message parser", e);
		}
	}

	public Message parse(File file) throws JAXBException, FileNotFoundException {
		return parse(new FileInputStream(file));
	}

	public Message parse(InputStream in) throws JAXBException {
		return (Message) messageUnmarshaller.unmarshal(in);
	}
}

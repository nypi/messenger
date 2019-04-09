package ru.croc.jws.messenger.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.croc.jws.messenger.common.Message;
import ru.croc.jws.messenger.common.User;

public class App extends Application {

	private Client client;

	private User user;

	public static void main(String[] args) {
		launch(args);
	}

	public App() {
		this.client = new Client("localhost", 7777);
		this.user = new User("anonymous");
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		int maxWidth = 1000;
		int maxHeight = 1000;

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));

		Label chatName = new Label("Chat with daisy:");
		grid.add(chatName, 0, 0, 2, 1);

		VBox chatBox = new VBox();
		chatBox.setFillWidth(false);
		ScrollPane scroll = new ScrollPane(chatBox);
		scroll.setBackground(new Background(
				new BackgroundFill(Color.TRANSPARENT, null, null)));
		scroll.setPrefWidth(maxWidth);
		scroll.setPrefHeight(maxHeight);
		grid.add(scroll, 0, 1, 2, 1);

		// sample message
		VBox messageFromDaisy = createMessageBox(
				"daisy",
				"Hi :)",
				new Date());
		chatBox.getChildren().add(messageFromDaisy);

		TextField messageField = new TextField();
		messageField.setPrefWidth(maxWidth);
		grid.add(messageField, 0, 2);

		Button send = new Button("Say!");
		send.setMinWidth(70);
		send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String text = messageField.getText();

				Message message = new Message(user, text);
				VBox messageBox = createMessageBox(
						message.getUser().getName(),
						message.getText(),
						message.getTime());
				chatBox.getChildren().add(messageBox);
				scroll.setVvalue(1.0);

				try {
					client.sendMessage(message);
					messageField.setText("");
				} catch (IOException e) {
					messageBox.setBackground(new Background(new BackgroundFill(
							Color.RED,
							new CornerRadii(8),
							Insets.EMPTY)));
					e.printStackTrace();
				}
			}
		});
		messageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER))
					send.fire();
			}
		});
		grid.add(send, 1, 2);

		Scene scene = new Scene(grid, 400, 500);
		primaryStage.setTitle("Messenger");
		primaryStage.setScene(scene);
		primaryStage.show();

		// load messages from server
		List<Message> messages = client.getMessages(new Date(0L));
		for (Message message : messages) {
			VBox messageBox = createMessageBox(
					message.getUser().getName(),
					message.getText(),
					message.getTime());
			chatBox.getChildren().add(messageBox);
		}
	}

	private VBox createMessageBox(String username, String text, Date time) {
		int messageWidth = 300;

		Color color = username.equals("me")
				? Color.WHITE
				: Color.BISQUE;

		VBox messageBox = new VBox();
		messageBox.setPadding(new Insets(3, 10, 3, 10));
		messageBox.setFillWidth(false);
		messageBox.setPrefWidth(messageWidth);
		messageBox.setBackground(new Background(new BackgroundFill(
				color,
				new CornerRadii(8),
				Insets.EMPTY)));
		// user
		Label messageUser = new Label();
		messageUser.setText(username);
		messageUser.setFont(Font.font(
				"Arial",
				FontWeight.BOLD,
				Font.getDefault().getSize()));
		messageUser.setPadding(new Insets(0, 0, 3, 0));
		messageBox.getChildren().add(messageUser);
		// text
		Text messageText = new Text();
		messageText.setText(text);
		messageText.setWrappingWidth(messageWidth);
		messageBox.getChildren().add(messageText);
		// time
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Label messageTime = new Label();
		messageTime.setText(sdf.format(time));
		messageTime.setTextFill(Color.DARKSEAGREEN);
		messageTime.setPadding(new Insets(3, 0, 0, 0));
		messageBox.getChildren().add(messageTime);

		VBox container = new VBox();
		container.setPadding(new Insets(3));
		container.getChildren().add(messageBox);
		container.setAlignment(Pos.TOP_RIGHT);
		return container;
	}
}

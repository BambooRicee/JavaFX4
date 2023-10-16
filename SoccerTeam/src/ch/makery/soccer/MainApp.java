package ch.makery.soccer;
import java.io.IOException;
import ch.makery.soccer.model.Person;
import ch.makery.soccer.view.PersonOverviewController;
import ch.makery.soccer.view.PersonEditDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * Данные, в виде наблюдаемого списка адресатов.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    /**
     * Конструктор
     */
    public MainApp() {
        // В качестве образца добавляем некоторые данные
        personData.add(new Person("Станислав", "Агкацев"));
        personData.add(new Person("Матвей", "Сафонов"));
        personData.add(new Person("Витор", "Тормена"));
        personData.add(new Person("Хуниор", "Алонсо"));
        personData.add(new Person("Лукас", "Оласа"));
        personData.add(new Person("Александр", "Эктов"));
        personData.add(new Person("Кайо", "-"));
        personData.add(new Person("Георгий", "Арутюнян"));
        personData.add(new Person("Сергей", "Волков"));
        personData.add(new Person("Сергей", "Петров"));
        personData.add(new Person("Кевин", " Пина"));
        personData.add(new Person("Ильзат", "Ахметов"));
        personData.add(new Person("Эдуард", "Сперцян"));
        personData.add(new Person("Жуан", "Баши"));
        personData.add(new Person("Михайло", "Баняц"));
        personData.add(new Person("Кади", "Боржис"));
        personData.add(new Person("Юрий", "Железнов"));
        personData.add(new Person("Александр", "Черников"));
        personData.add(new Person("Никита", "Кривцов"));
        personData.add(new Person("Джон", "Кордоба"));
        personData.add(new Person("Олакунле", "Олусегун"));
        personData.add(new Person("Мозес", "Кобнан"));
        personData.add(new Person("Александр", "Кокшаров"));
    }
    /**
     * Возвращает данные в виде наблюдаемого списка адресатов.
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Футбольный клуб");

        this.primaryStage.getIcons().add(new Image("file:resources/images/fc_krasnodar_logo.svg.png"));

        initRootLayout();

        showPersonOverview();
    }
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            // Даём контроллеру доступ к главному приложению.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   /**
    * Открывает диалоговое окно для изменения деталей указанного адресата.
    * Если пользователь кликнул OK, то изменения сохраняются в предоставленном
    * объекте адресата и возвращается значение true.
    *
    * @param person - объект адресата, который надо изменить
    * @return true, если пользователь кликнул OK, в противном случае false.
    */
   public boolean showPersonEditDialog(Person person) {
       try {
           // Загружаем fxml-файл и создаём новую сцену
           // для всплывающего диалогового окна.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Создаём диалоговое окно Stage.
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Edit Person");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(primaryStage);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Передаём адресата в контроллер.
           PersonEditDialogController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setPerson(person);
           
           dialogStage.getIcons().add(new Image("file:resources/images/103748_edit_close_user_icon.png"));

           // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
           dialogStage.showAndWait();

           return controller.isOkClicked();
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}

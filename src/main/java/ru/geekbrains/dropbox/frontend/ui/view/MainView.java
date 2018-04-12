package ru.geekbrains.dropbox.frontend.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.dropbox.frontend.service.FilesService;

import javax.annotation.PostConstruct;
import java.io.*;

@SpringView(name = "main")
public class MainView extends VerticalLayout implements View {

    @Autowired
    FilesService filesService;

    private Grid<File> gridFiles = new Grid<>();
    private FileDownloader fileDownloader;
    private Button btnDownload = new Button("Скачать");
    private Button btnDelete = new Button("Удалить");
    private Panel pnlActions = new Panel();


    @PostConstruct
    public void init() {
        VerticalLayout layoutSource = new VerticalLayout();
        layoutSource.setSizeUndefined();

        gridFiles.addColumn(File::getName).setCaption("File");
        gridFiles.setSizeFull();
        gridFiles.setItems(filesService.getFileList()); //грид отображает данные

        // Выбираем файл который скачаем
        gridFiles.addItemClickListener(new ItemClickListener<File>() {
            @Override
            public void itemClick(Grid.ItemClick<File> itemClick) {
                // Удаляем старый даунлоадер
                if (fileDownloader != null)
                    btnDownload.removeExtension(fileDownloader);

                // Создаем компонент который будет скачивать
                fileDownloader = new FileDownloader(
                        createResource(
                                itemClick.getItem().getName()
                        )
                );
                fileDownloader.extend(btnDownload);
            }
        });

        btnDelete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                gridFiles.getSelectedItems().iterator().forEachRemaining(file -> filesService.removeFile(file));
                gridFiles.setItems(filesService.getFileList());
            }
        });

        Upload uploadFile = new Upload();
        uploadFile.setButtonCaption("Загрузить");
        uploadFile.setImmediateMode(true);
        uploadFile.setReceiver(new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String fileName, String mimeType) {
                try {
                    return filesService.getFileOutputStream(fileName);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    Notification.show("Не удалось загрузить файл!").setDelayMsec(1000);
                }
                return null;
            }
        });
        uploadFile.addFinishedListener(finishedEvent -> gridFiles.setItems(filesService.getFileList()));

        HorizontalLayout layoutActions = new HorizontalLayout();
        layoutActions.setSizeUndefined();

        pnlActions.setSizeUndefined();
        pnlActions.setContent(layoutActions);
        layoutActions.addComponents(uploadFile, btnDelete, btnDownload);
        layoutSource.addComponents(gridFiles, pnlActions);
//        this.setContent(layoutSource);
    }

    private void startedUpload(Upload.StartedEvent event) {
        Notification.show("UploadStart");
    }

    private StreamResource createResource(String fileName) {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    return filesService.getFileInputStream(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }
        }, fileName);
    }
}

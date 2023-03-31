package dk.easv.gui.controllers;

import dk.easv.be.Ticket;
import dk.easv.gui.models.TicketViewModel;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketViewController implements Initializable {

    @FXML
    private AnchorPane bgAnchor;
    @FXML
    private ImageView imgView;

    private final TicketViewModel model = new TicketViewModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initialed(Ticket ticket, int eventId) throws IOException {
        Stage stage = (Stage) bgAnchor.getScene().getWindow();
        Image image = model.getTicketImage(ticket, eventId);
        stage.setMinWidth(image.getWidth());
        stage.setMinHeight(image.getHeight()+30);
        imgView.setImage(image);
        imgView.setFitWidth(image.getWidth());
        imgView.setFitHeight(image.getHeight());
        imgView.setViewport(new Rectangle2D(0, 0, image.getWidth(), image.getHeight()));
        stage.centerOnScreen();
        stage.setResizable(false);
        //Uncomment next line to start generating pdf files in the project directory
       // prepImageForExport();
    }

    public void prepImageForExport() throws IOException {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("x");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y");
        BarChart bar = new BarChart(xAxis, yAxis);
        bar.setMaxSize(300, 300);
        bar.setTitle("Bar node");
        bar.setTranslateY(-100);

        WritableImage nodeshot = bar.snapshot(new SnapshotParameters(), null);
        File file = new File("chart.png");

            ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            PDImageXObject pdimage;
            PDPageContentStream content;

                pdimage = PDImageXObject.createFromFile("chart.png", doc);
                content = new PDPageContentStream(doc, page);
                content.drawImage(pdimage, 100, 100, 2000, 647);
                content.close();
                doc.addPage(page);
                System.out.println("Saving ticket");
                doc.save("Ticket.pdf");
                doc.close();
                file.delete();
        }
    }

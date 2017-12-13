package at.ac.tuwien.sepm.assignment.group02.client.util;

import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * A helper class to create any kind of {@link Alert} dialogs.
 * The helper class follows the builder pattern and allows a fluent creation of {@link Alert} dialogs.
 *
 * @author Dominik Moser
 */
public class ExampleQSE_AlertBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private Alert.AlertType alertType;
    private String title;
    private String headerText;
    private String contentText;
    private Window owner;
    private Modality modality = Modality.NONE;
    private StageStyle stageStyle = StageStyle.DECORATED;

    /**
     * Set the type of the {@link Alert}.
     *
     * @param alertType the type of the alert
     * @return the dialog builder
     * @see Alert.AlertType
     */
    public ExampleQSE_AlertBuilder alertType(Alert.AlertType alertType) {
        this.alertType = alertType;
        return this;
    }

    /**
     * Set the title of the {@link Alert}.
     *
     * @param title the title of the alert
     * @return the dialog builder
     */
    public ExampleQSE_AlertBuilder title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set the header text of the {@link Alert}.
     *
     * @param headerText the header text of the alert
     * @return the dialog builder
     */
    public ExampleQSE_AlertBuilder headerText(String headerText) {
        this.headerText = headerText;
        return this;
    }

    /**
     * Set the content text of the {@link Alert}.
     *
     * @param contentText the content text of the alert
     * @return the dialog builder
     */
    public ExampleQSE_AlertBuilder contentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    /**
     * Set the owner of the {@link Alert}.
     *
     * @param owner the owner of the alert
     * @return the dialog builder
     * @see Window
     */
    public ExampleQSE_AlertBuilder owner(Window owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Set the modality of the {@link Alert}.
     *
     * @param modality the modality of the alert, defaults to Modality.NONE;
     * @return the dialog builder
     * @see Modality
     */
    public ExampleQSE_AlertBuilder modality(Modality modality) {
        this.modality = modality;
        return this;
    }

    /**
     * Set the stage style of the {@link Alert}.
     *
     * @param stageStyle the stage style of the alert, defaults to StageStyle.DECORATED
     * @return the dialog builder
     * @see StageStyle
     */
    public ExampleQSE_AlertBuilder stageStyle(StageStyle stageStyle) {
        this.stageStyle = stageStyle;
        return this;
    }

    /**
     * Build the {@link Alert} dialog which can then be shown to the user.
     *
     * @return the built alert dialog
     */
    public Alert build() {
        LOG.trace("Building dialog {}", this);
        Alert alert = new Alert(alertType);
        alert.setTitle(this.title);
        alert.setHeaderText(this.headerText);
        alert.setContentText(this.contentText);
        alert.initOwner(this.owner);
        alert.initModality(this.modality);
        alert.initStyle(this.stageStyle);
        return alert;
    }

    @Override
    public String toString() {
        return "ExampleQSE_AlertBuilder{" +
                "alertType=" + alertType +
                ", title='" + title + '\'' +
                ", headerText='" + headerText + '\'' +
                ", contentText='" + contentText + '\'' +
                ", owner=" + owner +
                ", modality=" + modality +
                ", stageStyle=" + stageStyle +
                '}';
    }
}
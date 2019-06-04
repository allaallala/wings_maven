package edu.msstate.nsparc.wings.integration.models.administrative;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.PropertiesResourceManager;

/**
 * Notes
 * Created by a.vnuchko on 06.04.2016.
 */
public class Notes {
    private Boolean notesCreate;
    private Boolean notesView;
    private Boolean notesEditPostNewButton;
    private Boolean notesEditRadioButton;
    private static final String NOTES_CREATE = "notesCreate";
    private static final String NOTES_VIEW = "notesView";
    private static final String NOTES_EDIT_POST = "notesEditPostNewButton";
    private static final String NOTES_EDIT_RADIO = "notesEditRadioButton";

    public Notes(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        notesCreate = Boolean.valueOf(prop.getProperty(NOTES_CREATE));
        notesView = Boolean.valueOf(prop.getProperty(NOTES_VIEW));
        notesEditPostNewButton = Boolean.valueOf(prop.getProperty(NOTES_EDIT_POST));
        notesEditRadioButton = Boolean.valueOf(prop.getProperty(NOTES_EDIT_RADIO));
    }

    public Boolean getNotesCreate() {
        return notesCreate;
    }

    public Boolean getNotesView() {
        return notesView;
    }

    public Boolean getNotesEditPostNewButton() {
        return notesEditPostNewButton;
    }

    public Boolean getNotesEditRadioButton() {
        return notesEditRadioButton;
    }
}

package netTest;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class Ch4_Contributions extends ApplicationWindow {
  StatusLineManager slm = new StatusLineManager();

  Ch4_StatusAction status_action = new Ch4_StatusAction(slm);

  ActionContributionItem aci = new ActionContributionItem(status_action);

  public Ch4_Contributions() {
    super(null);
    addStatusLine();
    addMenuBar();
    addToolBar(SWT.FLAT | SWT.WRAP);
  }

  protected Control createContents(Composite parent) {
    getShell().setText("Action/Contribution Example");
    parent.setSize(290, 150);
    aci.fill(parent);
    return parent;
  }

  public static void main(String[] args) {
    Ch4_Contributions swin = new Ch4_Contributions();
    swin.setBlockOnOpen(true);
    swin.open();
    Display.getCurrent().dispose();
  }

  protected MenuManager createMenuManager() {
    MenuManager main_menu = new MenuManager(null);
    MenuManager action_menu = new MenuManager("Menu");
    main_menu.add(action_menu);
    action_menu.add(status_action);
    return main_menu;
  }

  protected ToolBarManager createToolBarManager(int style) {
    ToolBarManager tool_bar_manager = new ToolBarManager(style);
    tool_bar_manager.add(status_action);
    return tool_bar_manager;
  }

  protected StatusLineManager createStatusLineManager() {
    return slm;
  }
}

class Ch4_StatusAction extends Action implements IAction {
  StatusLineManager statman;

  short triggercount = 0;

  public Ch4_StatusAction(StatusLineManager sm) {
    super("&Trigger@Ctrl+T", AS_PUSH_BUTTON);
    statman = sm;
    setToolTipText("Trigger the Action");
    setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(),
        "eclipse.gif"));
  }

  public void run() {
    triggercount++;
    statman.setMessage("The status action has fired. Count: "
        + triggercount);
  }

@Override
public void addPropertyChangeListener(IPropertyChangeListener arg0) {
	// TODO Auto-generated method stub

}

@Override
public int getAccelerator() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public String getActionDefinitionId() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getDescription() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ImageDescriptor getDisabledImageDescriptor() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public HelpListener getHelpListener() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ImageDescriptor getHoverImageDescriptor() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getId() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ImageDescriptor getImageDescriptor() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public IMenuCreator getMenuCreator() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getStyle() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public String getText() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getToolTipText() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isChecked() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isHandled() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void removePropertyChangeListener(IPropertyChangeListener arg0) {
	// TODO Auto-generated method stub

}

@Override
public void runWithEvent(Event arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setAccelerator(int arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setActionDefinitionId(String arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setChecked(boolean arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setDescription(String arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setDisabledImageDescriptor(ImageDescriptor arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setEnabled(boolean arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setHelpListener(HelpListener arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setHoverImageDescriptor(ImageDescriptor arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setId(String arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setImageDescriptor(ImageDescriptor arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setMenuCreator(IMenuCreator arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setText(String arg0) {
	// TODO Auto-generated method stub

}

@Override
public void setToolTipText(String arg0) {
	// TODO Auto-generated method stub

}
}
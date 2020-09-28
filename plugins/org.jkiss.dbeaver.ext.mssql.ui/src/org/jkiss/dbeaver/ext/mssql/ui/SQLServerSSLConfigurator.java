package org.jkiss.dbeaver.ext.mssql.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.jkiss.dbeaver.ext.mssql.SQLServerConstants;
import org.jkiss.dbeaver.model.net.DBWHandlerConfiguration;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.controls.TextWithOpen;
import org.jkiss.dbeaver.ui.controls.TextWithOpenFile;
import org.jkiss.dbeaver.ui.dialogs.net.SSLConfiguratorAbstractUI;
import org.jkiss.utils.CommonUtils;


public class SQLServerSSLConfigurator extends SSLConfiguratorAbstractUI {
    private TextWithOpen keystoreFile;
    private Text keystorePassword;
    private Text keystoreHostname;
    private Button trustServerCert;

    @Override
    public void createControl(Composite parent, Runnable propertyChangeListener) {
        final Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.minimumHeight = 200;
        composite.setLayoutData(gridData);

        createSSLConfigHint(composite, true, 1);

        {
            Group keyStoreGroup = UIUtils.createControlGroup(composite, "Keystore", 2, GridData.FILL_HORIZONTAL, -1);

            UIUtils.createControlLabel(keyStoreGroup, "Keystore");
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.minimumWidth = 130;
            keystoreFile = new TextWithOpenFile(keyStoreGroup, "Select keystore file", new String[]{"*.pfx;*.jks"});
            keystoreFile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            UIUtils.createControlLabel(keyStoreGroup, "Keystore password");
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.minimumWidth = 130;
            keystorePassword = new Text(keyStoreGroup, SWT.BORDER | SWT.PASSWORD);
            keystorePassword.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            UIUtils.createControlLabel(keyStoreGroup, "Keystore hostname");
            gridData = new GridData(GridData.FILL_HORIZONTAL);
            gridData.minimumWidth = 130;
            keystoreHostname = new Text(keyStoreGroup, SWT.BORDER);
            keystoreHostname.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            keystoreHostname.setToolTipText("The host name to be used in validating the SQL Server TLS/SSL certificate.");
        }

        trustServerCert = UIUtils.createCheckbox(composite, SQLServerUIMessages.dialog_setting_trust_server_certificate, SQLServerUIMessages.dialog_setting_trust_server_certificate_tip, true, 2);
    }

    @Override
    public void loadSettings(DBWHandlerConfiguration configuration) {
        keystoreFile.setText(CommonUtils.notEmpty(configuration.getStringProperty(SQLServerConstants.PROP_SSL_KEYSTORE)));
        keystorePassword.setText(CommonUtils.notEmpty(configuration.getStringProperty(SQLServerConstants.PROP_SSL_KEYSTORE_PASSWORD)));
        keystoreHostname.setText(CommonUtils.notEmpty(configuration.getStringProperty(SQLServerConstants.PROP_SSL_KEYSTORE_HOSTNAME)));
        trustServerCert.setSelection(configuration.getBooleanProperty(SQLServerConstants.PROP_SSL_TRUST_SERVER_CERT));
    }

    @Override
    public void saveSettings(DBWHandlerConfiguration configuration) {
        configuration.setProperty(SQLServerConstants.PROP_SSL_KEYSTORE, keystoreFile.getText().trim());
        configuration.setProperty(SQLServerConstants.PROP_SSL_KEYSTORE_PASSWORD, keystorePassword.getText().trim());
        configuration.setProperty(SQLServerConstants.PROP_SSL_KEYSTORE_HOSTNAME, keystoreHostname.getText().trim());
        configuration.setProperty(SQLServerConstants.PROP_SSL_TRUST_SERVER_CERT, trustServerCert.getSelection());
    }
}

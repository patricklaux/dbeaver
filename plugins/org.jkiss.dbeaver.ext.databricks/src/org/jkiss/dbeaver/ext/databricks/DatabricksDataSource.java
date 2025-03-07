/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2023 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.databricks;

import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.ext.generic.model.GenericDataSource;
import org.jkiss.dbeaver.ext.generic.model.meta.GenericMetaModel;
import org.jkiss.dbeaver.model.DBPDataSourceContainer;
import org.jkiss.dbeaver.model.connection.DBPConnectionConfiguration;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;
import org.jkiss.utils.CommonUtils;

public class DatabricksDataSource extends GenericDataSource {

    private static final Log log = Log.getLog(DatabricksDataSource.class);

    public DatabricksDataSource(DBRProgressMonitor monitor,
                                DBPDataSourceContainer container,
                                GenericMetaModel metaModel) throws
                                                            DBException {
        super(monitor, container, metaModel, new DatabricksSQLDialect());
    }


    @Override
    protected String getConnectionURL(DBPConnectionConfiguration connectionInfo) {
        String url = super.getConnectionURL(connectionInfo);
        if (!isLegacyDriver() && url.startsWith(DatabricksConstants.JDBC_LEGACY_URL_SUBPROTOCOL)) {
            log.debug("Detected a legacy connection URL in the Databricks native driver. Updating to the native URL.");
            url = url.replaceFirst(DatabricksConstants.JDBC_LEGACY_URL_SUBPROTOCOL, "jdbc:databricks://");
        }
        return url;
    }

    private boolean isLegacyDriver() {
        return CommonUtils.equalObjects(DatabricksConstants.DRIVER_CLASS_LEGACY, getContainer().getDriver().getDriverClassName());
    }

}

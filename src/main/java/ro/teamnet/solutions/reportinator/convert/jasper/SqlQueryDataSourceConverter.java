/*
 * Copyright (c) 2015 Teamnet S.A. All Rights Reserved.
 *
 * This source file may not be copied, modified or redistributed,
 * in whole or in part, in any form or for any reason, without the express
 * written consent of Teamnet S.A.
 */

package ro.teamnet.solutions.reportinator.convert.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import ro.teamnet.solutions.reportinator.convert.DataSourceConverter;

import java.sql.Connection;
import java.util.Collection;

/**
 * Converts the results of a SQL query string execution to a {@link net.sf.jasperreports.engine.JRDataSource} (specific to
 * JasperReports) through some internal heuristics.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/6/2015
 * @deprecated <strong>NOT IMPLEMENTED YET.</strong>
 */
@Deprecated
public final class SqlQueryDataSourceConverter implements DataSourceConverter<String, JRDataSource> {

    private Connection connection;

    public SqlQueryDataSourceConverter(Connection connection, Collection<String> fieldMetadata) {
        this.connection = connection;
    }

    @Override
    public JRDataSource convert(String sqlString) {
        // FUTURE Implement this
        throw new IllegalStateException("Not implemented yet.");
    }

    @Override
    public Collection<String> getFieldMetadata() {
        // FUTURE Implement this
        throw new IllegalStateException("Not implemented yet.");
    }

}

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: http://www.jooq.org/licenses
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.impl;

import static org.jooq.Clause.TABLE_VALUES;
// ...
import static org.jooq.impl.Keywords.K_MULTISET;
import static org.jooq.impl.Keywords.K_TABLE;
import static org.jooq.impl.Keywords.K_VALUES;

import org.jooq.Context;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.Table;

/**
 * An implementation for the <code>VALUES(...)</code> table constructor
 *
 * @author Lukas Eder
 */
final class Values<R extends Record> extends AbstractTable<R> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -637982217747670311L;

    private final Row[]       rows;

    Values(Row[] rows) {
        super("values");

        this.rows = assertNotEmpty(rows);
    }

    static Row[] assertNotEmpty(Row[] rows) {
        if (rows == null || rows.length == 0)
            throw new IllegalArgumentException("Cannot create a VALUES() constructor with an empty set of rows");

        return rows;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final Class<? extends R> getRecordType() {
        return (Class<? extends R>) RecordImpl.class;
    }

    @Override
    public final Table<R> as(Name alias) {
        return new TableAlias<>(this, alias, true);
    }

    @Override
    public final Table<R> as(Name alias, Name... fieldAliases) {
        return new TableAlias<>(this, alias, fieldAliases, true);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {

            // [#915] Emulate VALUES(..) with SELECT .. UNION ALL SELECT ..
            // for those dialects that do not support a VALUES() constructor














            case FIREBIRD:
            case MARIADB:
            case MYSQL: {
                Select<Record> selects = null;

                for (Row row : rows) {
                    Select<Record> select = DSL.select(row.fields());

                    if (selects == null)
                        selects = select;
                    else
                        selects = selects.unionAll(select);
                }

                ctx.formatIndentStart()
                   .formatNewLine()
                   .subquery(true)
                   .visit(selects)
                   .subquery(false)
                   .formatIndentEnd()
                   .formatNewLine();
                break;
            }

            // [#915] Native support of VALUES(..)
            case CUBRID:
            case DERBY:
            case H2:
            case HSQLDB:
            case POSTGRES:
            case SQLITE:







            default: {
                ctx.start(TABLE_VALUES);






                ctx.visit(K_VALUES);

                if (rows.length > 1)
                    ctx.formatIndentStart()
                       .formatSeparator();

                for (int i = 0; i < rows.length; i++) {
                    if (i > 0)
                        ctx.sql(',')
                           .formatSeparator();

                    ctx.visit(rows[i]);
                }

                if (rows.length > 1)
                    ctx.formatIndentEnd()
                       .formatNewLine();





                ctx.end(TABLE_VALUES);
                break;
            }
        }
    }

    @Override
    final Fields<R> fields0() {
        return new Fields<>(rows[0].fields());
    }
}

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
package org.jooq;

// ...
// ...
// ...
// ...
import static org.jooq.SQLDialect.CUBRID;
// ...
import static org.jooq.SQLDialect.DERBY;
import static org.jooq.SQLDialect.FIREBIRD;
import static org.jooq.SQLDialect.H2;
// ...
import static org.jooq.SQLDialect.HSQLDB;
// ...
// ...
import static org.jooq.SQLDialect.MARIADB;
import static org.jooq.SQLDialect.MYSQL;
// ...
import static org.jooq.SQLDialect.POSTGRES;
// ...
// ...
import static org.jooq.SQLDialect.SQLITE;
// ...
// ...
// ...
// ...

import org.jooq.impl.DSL;


/**
 * An intermediate type for the construction of a relational division
 * <p>
 * <h3>Referencing <code>XYZ*Step</code> types directly from client code</h3>
 * <p>
 * It is usually not recommended to reference any <code>XYZ*Step</code> types
 * directly from client code, or assign them to local variables. When writing
 * dynamic SQL, creating a statement's components dynamically, and passing them
 * to the DSL API statically is usually a better choice. See the manual's
 * section about dynamic SQL for details: <a href=
 * "https://www.jooq.org/doc/latest/manual/sql-building/dynamic-sql">https://www.jooq.org/doc/latest/manual/sql-building/dynamic-sql</a>.
 * <p>
 * Drawbacks of referencing the <code>XYZ*Step</code> types directly:
 * <ul>
 * <li>They're operating on mutable implementations (as of jOOQ 3.x)</li>
 * <li>They're less composable and not easy to get right when dynamic SQL gets
 * complex</li>
 * <li>They're less readable</li>
 * <li>They might have binary incompatible changes between minor releases</li>
 * </ul>
 *
 * @author Lukas Eder
 */
public interface DivideByOnStep {

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause, connecting
     * them with each other with {@link Operator#AND}.
     */
    @Support
    DivideByOnConditionStep on(Condition condition);

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause, connecting
     * them with each other with {@link Operator#AND}.
     */
    @Support
    DivideByOnConditionStep on(Condition... conditions);

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause
     */
    @Support
    DivideByOnConditionStep on(Field<Boolean> condition);

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause
     *
     * @deprecated - 3.8.0 - [#4763] - Use {@link #on(Condition)} (typically
     *             with {@link DSL#trueCondition()},
     *             {@link DSL#falseCondition()}, or {@link DSL#noCondition()} as
     *             the parameter) or {@link #on(Field)} instead. Due to
     *             ambiguity between calling this method using
     *             {@link Field#equals(Object)} argument, vs. calling the other
     *             method via a {@link Field#equal(Object)} argument, this
     *             method will be removed in the future.
     */
    @Deprecated
    @Support
    DivideByOnConditionStep on(Boolean condition);

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(SQL)
     * @see SQL
     */
    @Support
    @PlainSQL
    DivideByOnConditionStep on(SQL sql);

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String)
     * @see SQL
     */
    @Support
    @PlainSQL
    DivideByOnConditionStep on(String sql);

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, Object...)
     * @see DSL#sql(String, Object...)
     * @see SQL
     */
    @Support
    @PlainSQL
    DivideByOnConditionStep on(String sql, Object... bindings);

    /**
     * Add a division condition to the <code>DIVIDE BY</code> clause
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, QueryPart...)
     * @see DSL#sql(String, QueryPart...)
     * @see SQL
     */
    @Support
    @PlainSQL
    DivideByOnConditionStep on(String sql, QueryPart... parts);
}

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

import static org.jooq.impl.DSL.one;
import static org.jooq.impl.DSL.zero;
import static org.jooq.impl.Keywords.F_SGN;
import static org.jooq.impl.Keywords.F_SIGN;

import org.jooq.Context;
import org.jooq.Field;

/**
 * @author Lukas Eder
 */
final class Sign extends AbstractField<Integer> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -7273879239726265322L;

    private final Field<?>    argument;

    Sign(Field<?> argument) {
        super(DSL.name("sign"), SQLDataType.INTEGER);

        this.argument = argument;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {






            case SQLITE:
                ctx.visit(DSL
                    .when(((Field<Integer>) argument).greaterThan(zero()), one())
                    .when(((Field<Integer>) argument).lessThan(zero()), one().neg())
                    .otherwise(zero()));
                break;

            default:
                ctx.visit(F_SIGN).sql('(').visit(argument).sql(')');
                break;
        }
    }
}

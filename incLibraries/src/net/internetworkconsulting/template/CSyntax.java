/*
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
package net.internetworkconsulting.template;

public class CSyntax implements SyntaxInterface {
    public String getVariable() { return "__NAME__"; }
    public String[] getVariableReplace() { return new String[] { "__" }; }
    public String getOpenBlock() { return "\\/\\/\\s*BEGIN\\s*NAME"; }
    public String[] getOpenBlockReplace() { return new String[] { "//", "BEGIN" }; }
    public String getCloseBlock() { return "\\/\\/\\s*END\\s*NAME"; }    
}

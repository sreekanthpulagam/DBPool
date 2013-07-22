/*
  ---------------------------------------------------------------------------
  DBPool : Java Database Connection Pooling <http://www.snaq.net/>
  Copyright (c) 2001-2013 Giles Winstanley. All Rights Reserved.

  This is file is part of the DBPool project, which is licenced under
  the BSD-style licence terms shown below.
  ---------------------------------------------------------------------------
  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
  this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

  3. The name of the author may not be used to endorse or promote products
  derived from this software without specific prior written permission.

  4. Redistributions of modified versions of the source code, must be
  accompanied by documentation detailing which parts of the code are not part
  of the original software.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER "AS IS" AND ANY EXPRESS OR
  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
  OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  ---------------------------------------------------------------------------
 */
package snaq.db;

import java.sql.*;

/**
 * Abstract {@link ConnectionValidator} implementation that validates
 * database connections by issuing a SQL query.
 *
 * <p>To create a simple ConnectionValidator implementation which validates
 * using a SQL query, extend this class and implement the abstract methods.</p>
 * 
 * <p>For example, the {@link #getQueryString()} method could be implemented to
 * return the string:</p>
 * <blockquote><blockquote>
 * SELECT * FROM test WHERE test_id &lt; 10;
 * </blockquote></blockquote>
 * <p>This would cause this SQL query to be issued to the database whenever
 * a connection needs validating. If the query causes a {@link SQLException}
 * to be thrown then the validation automatically fails.
 * If the query completes successfully, the generated {@link ResultSet}
 * is then passed to the {@link #checkResults(ResultSet)} method.</p>
 * 
 * <p>This class is provided as a convenience for providing
 * connection validation.</p>
 * 
 * @author Giles Winstanley
 */
public abstract class QueryValidator implements ConnectionValidator
{
  /**
   * Determines whether the specified connection is good to use.
   * @param con {@link Connection} instance to check for validity
   * @return true if the specified connection is good to use, false otherwise
   */
  public final boolean isValid(Connection con) throws SQLException
  {
    Statement st = null;
    ResultSet res = null;
    try
    {
      st = con.createStatement();
      res = st.executeQuery(getQueryString());
      boolean ok = checkResults(res);
      res.close();
      res = null;
      st.close();
      st = null;
      return ok;
    }
    finally
    {
      if (res != null)
      {
        try { res.close(); }
        catch (SQLException sqlx) {}
      }
      if (st != null)
      {
        try { st.close(); }
        catch (SQLException sqlx) {}
      }
    }
  }

  /**
   * Returns the SQL query string to be issued to the database.
   * @return query string in SQL syntax
   */
  public abstract String getQueryString();

  /**
   * Checks the results of the SQL query to see if it indicates a valid connection.
   * @param results {@code ResultSet} instance produced from SQL query
   * @return boolean indicating whether checked results indicated a valid connection
   * @throws SQLException if checking the results throws such an exception
   */
  public abstract boolean checkResults(ResultSet results) throws SQLException;
}
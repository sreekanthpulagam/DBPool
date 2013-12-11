/*
  ---------------------------------------------------------------------------
  DBPool : Java Database Connection Pooling <http://www.snaq.net/>
  Copyright (c) 2001-2013 Giles Winstanley. All Rights Reserved.

  This is file is part of the DBPool project, which is licensed under
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
package snaq.util;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Interface defining an object that can notify an event listener.
 * This interface is used by the {@link EventDispatcher} class to perform
 * the event notification. The dispatcher schedules event notification
 * asynchronously, then for each actual event notification the single method
 * of this interface is called.
 *
 * @author Giles Winstanley
 * @param <L> class type of event listener
 * @param <E> class type of event object
 */
public interface EventNotifier<L extends EventListener, E extends EventObject>
{
  /**
   * Notifies the specified listener of the specified event.
   * Implementors should be aware that this method is not designed to throw
   * any exceptions, as it is called asynchronously by the
   * {@link EventDispatcher} thread. This includes cases of
   * {@link RuntimeException} which should be caught and dealt with,
   * otherwise the default behaviour is simply to print the stacktrace to
   * {@link System#err}.
   * @param listener listener to notify
   * @param event event for which listener is to be notified
   */
  void notifyListener(L listener, E event);
}

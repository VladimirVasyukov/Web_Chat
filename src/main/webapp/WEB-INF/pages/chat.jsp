<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Chat</title>
    <style>
      * {
        box-sizing: border-box;
      }

      body,
      html {
        height: 100%;
        margin: 0;
      }

      table {
        height: 100%;
        width: 100%;
      }

      table,
      td {
        border: 2px solid black;
        border-collapse: collapse;
        padding: 0;
      }

      .submit {
        height: 100%;
        width: 100%;
      }

      textarea {
        height: 100%;
        width: 100%;
        margin: 0;
        padding: 5px;
        resize: none;
        border: none;
        outline: none;
        background: transparent;
      }

      .list {
        overflow-y: auto;
        max-height: calc(
          100vh - 100px /* input */- 20px /* header */ - 2px * 3 /* borders */
        );
      }
    </style>

    <script type="text/javascript">
        function reply(nickname) {
            const input = document.getElementById("message");
            const text = input.value || '';
            input.value = nickname + ', ' + text;
        }

        function reload() {
            fetch('<c:url value="/controller?cmd=getLast&messageCount=30" />')
                .then(response => response.text())
                .then(result => {
                    const list = document.querySelector(".list");
                    list.innerHTML = result;
                    list.scrollTo(0, list.scrollHeight);
                })
                .catch(error => console.error(error));
        }

        function sendMessage() {
            const input = document.getElementById("message");
            const text = input.value || '';

            if (text) {
                fetch('<c:url value="/controller?cmd=sendMessage&messageText=" />' + text, {method: 'POST'})
                    .then(() => {
                        reload();
                    })
                    .then(() => {
                        input.value = '';
                    })
                    .catch(error => console.error(error));
            }
        }

        setInterval(reload, 2000);
    </script>
  </head>
  <body>
    <table>
      <tr style="height: 20px">
        <td style="text-align: end">
             Здравствуйте, ${currentUser.getNickname()}!
        </td>
        <td style="text-align: end">
          <a href='<c:url value="/controller?cmd=logout" />'>Выйти</a>
        </td>
      </tr>
      <tr style="vertical-align: top">
        <td>
          <div class="list">
              <jsp:include page='/message-list' />
          </div>
        </td>
        <td>
          <div class="list">
            <ul>
                <c:forEach var="user" items="${users}">
                    <li>
                        <c:choose>
                            <c:when test="${user.isKicked()}">
                                 <span style="color: red;">
                                    ${user.getNickname()}
                                 </span>
                            </c:when>
                            <c:when test="${currentUser.isAdmin()}">
                                 <span style="cursor: pointer;" onclick="reply('${user.getNickname()}')">
                                    ${user.getNickname()}
                                 </span>
                            </c:when>
                            <c:otherwise>
                                 ${user.getNickname()}
                            </c:otherwise>
                        </c:choose>
                         <c:if test="${currentUser.isAdmin()}">
                             <c:if test="${!user.isKicked()}">
                                <c:if test="${!user.isAdmin()}">
                                     <form
                                         method="post"
                                         action='<c:url value="/controller?cmd=kick&nickname=${currentUser.getNickname()}&kickableUser=${user.getNickname()}" />'
                                         style="display: inline;"
                                     >
                                        <button>X</button>
                                     </form>
                                 </c:if>
                             </c:if>
                         </c:if>
                         <c:if test="${currentUser.isAdmin()}">
                              <c:if test="${user.isKicked()}">
                                      <form
                                          method="post"
                                          action='<c:url value="/controller?cmd=unkick&nickname=${currentUser.getNickname()}&kickedUser=${user.getNickname()}" />'

                                          style="display: inline;"
                                      >
                                         <button>O</button>
                                      </form>

                              </c:if>
                         </c:if>
                    </li>
                </c:forEach>
            </ul>
          </div>
        </td>
      </tr>
      <tr style="height: 100px">
        <td>
          <textarea
            id="message"
            name="messageText"
            placeholder="Введите сообщение"
          ></textarea>
        </td>
        <td>
          <button class="submit" onclick="sendMessage()">Отправить</button>
        </td>
      </tr>
    </table>
  </body>
</html>

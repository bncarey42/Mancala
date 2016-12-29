<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Mancala</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://fonts.googleapis.com/css?family=Bungee|Roboto+Slab" rel="stylesheet"> 
        <link href="style.css" rel="stylesheet" type="text/css"> 
    </head>
    <body>
        <h1>MANCALA!</h1>
        <div class="scorecard">
            <p id="player1">Player1 Score: ${game.player1Score}</p>
            <p id="player2">Player2 Score: ${game.player2Score}</p>
            <c:if test="${game.winner == 0}">
                <p>Points left: ${game.gameBeanPool}</p>
                <h3 id="turnNum">It is Player ${game.playerNum}'s turn</h3>
            </c:if>
        </div>
        <div class="gameboard">
            <div class="main">
                <c:choose>
                    <c:when test="${game.winner == 0}">
                        <table>
                            <tbody>
                                <tr class="player2">
                                    <td rowspan="2" class="home" id="p2">
                                        <p>${game.board[0]}</p>

                                    </td>
                                    <c:forEach var="pit" items="${game.board}" varStatus="pitStatus" begin="1" end="6">
                                        <td class="pits">
                                            <form action="<c:url value='sow'/>" method="post">  
                                                <input type="hidden" value="${14 - pitStatus.index}" name="index"/>
                                                <input type="submit" value="${game.board[14 - pitStatus.index]}" 
                                                       ${game.playerNum != 2 || game.board[14 - pitStatus.index] == 0 ? "disabled" : ""}/>

                                            </form>
                                        </td>
                                    </c:forEach>
                                    <td rowspan="2" class="home" id="p1">
                                        <p>${game.board[7]}</p>

                                    </td>
                                </tr>
                                <tr class="player1">
                                    <c:forEach var="pit" items="${game.board}" varStatus="pitStatus" begin="1" end="7" step="1">
                                        <c:if test="${pitStatus.index > 0 
                                                      && pitStatus.index != 7}">
                                              <td class="pits">
                                                  <form action="<c:url value='sow'/>" method="post">

                                                      <input type="hidden" value="${pitStatus.index}" name="index"/>
                                                      <input type="submit" value="${pit}" title="" ${game.playerNum != 1 || pit == 0 ? "disabled" : ""}/>
                                                  </form>
                                              </td>
                                        </c:if>
                                    </c:forEach>
                                </tr>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="win">
                            <h1 id="itsOverMsg">${game.winMsg}</h1>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <br />
        <br />

        <form action="<c:url value='sow' />" >
            <input type="submit" value="Reset Board" name="reset" />
            <input type="submit" value="Quit Game" name="quit" />
        </form>

    </body>
</html>

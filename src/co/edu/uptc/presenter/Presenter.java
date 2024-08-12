package co.edu.uptc.presenter;

import co.edu.uptc.model.Person;
import co.edu.uptc.model.PointAttention;
import co.edu.uptc.view.View;

public class Presenter {
  private View view;
  private PointAttention[] pointAttentions;
  private int totalTime;
  private int totalPeople;

  public Presenter() {
    this.view = new View();
  }

  public void startSimulation(int totalTime, int numPoints) {
    this.totalTime = totalTime;
    pointAttentions = new PointAttention[numPoints];
    view.showMessage("*************************************");
    view.showMessage("Iniciando simulación");
    view.showMessage("*************************************");
    for (int i = 0; i < numPoints; i++) {
      pointAttentions[i] = new PointAttention((int) (Math.random() * 10) + 15, i);
      int random = (int) (Math.random() * 23) + 8;
      totalPeople += random;
      for (int j = 0; j < random; j++) {
        pointAttentions[i].addPerson(new Person("Persona " + j));
      }
      view.showMessage("=====================================");
      view.showMessage("PUNTO DE ATENCIÓN " + (i + 1));
      view.showMessage("Personas en cola: " + pointAttentions[i].getQueueUsers().size());
      view.showMessage("=====================================");
      pointAttentions[i].start();
    }

    try {
      Thread.sleep(totalTime * 1000);
      for (int i = 0; i < numPoints; i++) {
        pointAttentions[i].interrupt();
      }
    } catch (InterruptedException e) {
      view.showError("Error en la simulación");
    }
  }

  public void showResults() {
    view.showMessage("");
    view.showMessage("*************************************");
    view.showMessage("Resultados de la simulación");
    view.showMessage("*************************************");

    int totalAttendedUsers = 0;
    int totalWithoutAttentionUsers = 0;
    for (int i = 0; i < pointAttentions.length; i++) {
      view.showMessage("=====================================");
      view.showMessage("PUNTO DE ATENCIÓN " + (i + 1));
      view.showMessage("Tiempos de atención del empleado: " + pointAttentions[i].getTimeAttention() + " minutos");
      view.showMessage("Atendió a: " + pointAttentions[i].getAttentedUsers() + " personas");
      totalAttendedUsers += pointAttentions[i].getAttentedUsers();
      view.showMessage("Dejó sin atención a: " + pointAttentions[i].getWithoutAttentionUsers() + " personas");
      totalWithoutAttentionUsers += pointAttentions[i].getWithoutAttentionUsers();

      view.showMessage("=====================================");
    }
    view.showMessage("");
    view.showMessage("*************************************");
    view.showMessage("RESULTADOS GENERALES");
    view.showMessage("La simulación duró " + this.totalTime + " horas.");
    view.showMessage("Total de personas atendidas: " + totalAttendedUsers);
    view.showMessage("Total de personas sin atención: " + totalWithoutAttentionUsers);
    view.showMessage("Total de personas en la simulación: " + totalPeople);
    view.showMessage("*************************************");
  }

  public void run() {
    view.showMessage("BIENVENIDO AL SIMULADOR DE COLAS PARA PUNTOS DE ATENCIÓN EN UN SUPERMERCADO");
    try {
      view.showMessage("Ingrese el tiempo de simulación en horas");
      int totalTime = Integer.parseInt(view.readData());
      view.showMessage("Ingrese el número de puntos de atención");
      int numPoints = Integer.parseInt(view.readData());
      startSimulation(totalTime, numPoints);
      showResults();
    } catch (NumberFormatException e) {
      view.showError("Debe ingresar un número entero");
    }
  }

  public static void main(String[] args) {
    Presenter presenter = new Presenter();
    presenter.run();
  }
}

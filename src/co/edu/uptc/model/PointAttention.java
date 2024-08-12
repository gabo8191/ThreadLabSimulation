package co.edu.uptc.model;

import co.edu.uptc.structures.MyQueue;

public class PointAttention extends Thread {

  private MyQueue<Person> queueUsers;
  private int timeAttention;
  private int attentedUsers;
  private int withoutAttentionUsers;
  private int id;

  public PointAttention(int timeAttention, int id) {
    this.queueUsers = new MyQueue<>();
    this.timeAttention = timeAttention;

    this.attentedUsers = 0;
    this.withoutAttentionUsers = 0;
    this.id = id;
  }

  public void addPerson(Person person) {
    queueUsers.push(person);
  }

  @Override
  public void run() {
    while (!queueUsers.isEmpty() && !isInterrupted()) {
      try {
        Thread.sleep(timeAttention * 1000 / 60);
        Person person = queueUsers.pull();
        attentedUsers++;
      } catch (InterruptedException e) {
        interrupt();
      }
    }
    withoutAttentionUsers = queueUsers.size();
  }

  public int getAttentedUsers() {
    return attentedUsers;
  }

  public int getWithoutAttentionUsers() {
    return withoutAttentionUsers;
  }

  public MyQueue<Person> getQueueUsers() {
    return queueUsers;
  }

  public int getIdClass() {
    return id;
  }

  public int getTimeAttention() {
    return timeAttention;
  }

}

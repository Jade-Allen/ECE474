/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece474;

/**
 *
 * @author steve
 */
public class RS {

    private boolean busy;
    private int op;
    private int vj;
    private int vk;
    private int qj;
    private int qk;
    private int disp;
    private int result;
    private int dest;
    private int source1;
    private int source2;

    public int getSource1() {
        return source1;
    }

    public void setSource1(int source1) {
        this.source1 = source1;
    }

    public int getSource2() {
        return source2;
    }

    public void setSource2(int source2) {
        this.source2 = source2;
    }
    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public RS() {

    }

    public boolean getBusy() {
        return busy;
    }

    public int getOp() {
        return op;
    }

    public int getVj() {
        return vj;
    }

    public int getVk() {
        return vk;
    }

    public int getQj() {
        return qj;
    }

    public int getQk() {
        return qk;
    }

    public int getDisp() {
        return disp;
    }

    public int getResult() {
        return result;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public void setVj(int vj) {
        this.vj = vj;
    }

    public void setVk(int vk) {
        this.vk = vk;
    }

    public void setQj(int qj) {
        this.qj = qj;
    }

    public void setQk(int qk) {
        this.qk = qk;
    }

    public void setDisp(int disp) {
        this.disp = disp;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

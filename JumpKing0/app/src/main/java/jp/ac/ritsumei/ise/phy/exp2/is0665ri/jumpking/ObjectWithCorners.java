package jp.ac.ritsumei.ise.phy.exp2.is0665ri.jumpking;

import java.util.ArrayList;
import java.util.List;

class ObjectWithCorners {
    List<Rectangle> corners;

    public ObjectWithCorners() {
        this.corners = new ArrayList<>();
    }

    public void addCorner(Rectangle corner) {
        corners.add(corner);
    }

    public List<Rectangle> getObjectCorners() {
        return corners;
    }
}
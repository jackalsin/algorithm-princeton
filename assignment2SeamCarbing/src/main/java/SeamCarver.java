import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author zhixi
 * @version 1.0 on 12/31/2019
 */
public class SeamCarver {
  private static final int BOUNDARY_ENERGY = 1000;
  private Picture picture;
  private double[][] energy;
  private boolean isTransposed = false;

  // create a seam carver object based on the given picture
  public SeamCarver(Picture picture) {
    if (picture == null) {
      throw new IllegalArgumentException();
    }
    setPicture(new Picture(picture));
  }

  // current picture
  public Picture picture() {
    if (isTransposed) {
      transpose();
    }
    return new Picture(picture);
  }

  // width of current picture
  public int width() {
    if (isTransposed) {
      transpose();
    }
    return picture.width();
  }

  // height of current picture
  public int height() {
    if (isTransposed) {
      transpose();
    }
    return picture.height();
  }

  private void setPicture(final Picture picture) {
    this.picture = picture;
    this.energy = energy(picture);
  }

  // energy of pixel at column x and row y
  public double energy(int x, int y) {
    final int width = isTransposed ? picture.height() : picture.width(),
        height = isTransposed ? picture.width() : picture.height();
    if (x < 0 || y < 0 || x >= width || y >= height) {
      throw new IllegalArgumentException();
    }
    if (isTransposed) {
      transpose();
    }
    return energy[y][x];
  }

  private static double[][] energy(final Picture picture) {
    final int width = picture.width(), height = picture.height();
    final double[][] result = new double[height][width];
    for (int h = 0; h < height; ++h) {
      for (int w = 0; w < width; ++w) {
        if (w == 0 || h == 0 || w == width - 1 || h == height - 1) {
          result[h][w] = BOUNDARY_ENERGY;
        } else {
          final int[] left = getRgb(picture.getRGB(w - 1, h)),
              right = getRgb(picture.getRGB(w + 1, h)),
              top = getRgb(picture.getRGB(w, h - 1)),
              bottom = getRgb(picture.getRGB(w, h + 1));

          final int sum = square(left[0] - right[0])
              + square(left[1] - right[1])
              + square(left[2] - right[2])
              + square(top[0] - bottom[0])
              + square(top[1] - bottom[1])
              + square(top[2] - bottom[2]);
          result[h][w] = Math.sqrt(sum);
        }
      }
    }
    return result;
  }

  // no need to care about the rgb order
  private static int[] getRgb(final int rgb) {
    final int[] result = new int[3];
    result[0] = rgb & 0xff;
    result[1] = (rgb >> 8) & 0xff;
    result[2] = (rgb >> 16) & 0xff;
    return result;
  }

  private static int square(final int num) {
    return num * num;
  }

  // sequence of indices for horizontal seam
  public int[] findHorizontalSeam() {
    if (isTransposed) {
      transpose();
    }
    return findHorizontalSeamHelper();
  }

  private static boolean isValid(final int width, final int height, final int x, final int y) {
    return 0 <= x && x < width && 0 <= y && y < height;
  }

  // sequence of indices for vertical seam
  public int[] findVerticalSeam() {
    if (!isTransposed) {
      transpose();
    }
    return findHorizontalSeamHelper();
  }


  private int[] findHorizontalSeamHelper() {
    final int width = picture.width(), height = picture.height();
    final double[][] minEnergy = new double[height][width];
    for (double[] min : minEnergy) {
      Arrays.fill(min, Double.POSITIVE_INFINITY);
    }
    final Queue<Point> pq = new PriorityQueue<>(Comparator.comparingDouble(c -> minEnergy[c.h][c.w]));
    final Point[][] edgeFrom = new Point[height][width];
    for (int h = 0; h < height; ++h) {
      minEnergy[h][0] = BOUNDARY_ENERGY;
      pq.add(new Point(0, h));
    }

    Point finalPoint;
    while (true) {
      assert !pq.isEmpty();
      final Point toRemove = pq.remove();
      if (toRemove.w == width - 1) {
        finalPoint = toRemove;
        break;
      }
      final double fromEnergy = minEnergy[toRemove.h][toRemove.w];
      for (int dy : new int[]{-1, 0, 1}) {
        final int nextW = toRemove.w + 1, nextH = toRemove.h + dy;
        if (!isValid(width, height, nextW, nextH)) continue;
        final Point nextP = new Point(nextW, nextH);
        final double cur = energy[nextH][nextW], nextEnergy = cur + fromEnergy;
        if (nextEnergy < minEnergy[nextH][nextW]) {
          minEnergy[nextH][nextW] = nextEnergy;
          pq.add(nextP);
          edgeFrom[nextH][nextW] = toRemove;
        }
      } // end of for
    }
    final int[] result = new int[picture.width()];
    for (int i = result.length - 1; i >= 0; --i, finalPoint = edgeFrom[finalPoint.h][finalPoint.w]) {
      result[i] = finalPoint.h;
    }
    return result;
  }

  // remove horizontal seam from current picture
  public void removeHorizontalSeam(int[] seam) {
    if (seam == null || seam.length != width() || !isContinuousSeam(seam)) {
      throw new IllegalArgumentException();
    }
    final Picture newPicture = new Picture(width(), height() - 1);
    for (int w = 0; w < width(); w++) {
      for (int h = 0; h < height(); h++) {
        if (h < seam[w]) {
          newPicture.set(w, h, picture.get(w, h));
        } else if (h > seam[w]) {
          newPicture.set(w, h - 1, picture.get(w, h));
        }
      }
    }
    setPicture(newPicture);
  }

  // remove vertical seam from current picture
  public void removeVerticalSeam(int[] seam) {
    if (seam == null || seam.length != height() || !isContinuousSeam(seam)) {
      throw new IllegalArgumentException();
    }
    final Picture newPicture = new Picture(width() - 1, height());
    for (int w = 0; w < width(); w++) {
      for (int h = 0; h < height(); h++) {
        if (w < seam[h]) {
          newPicture.set(w, h, picture.get(w, h));
        } else if (w > seam[h]) {
          newPicture.set(w - 1, h, picture.get(w, h));
        }
      }
    }
    setPicture(newPicture);
  }

  private static boolean isContinuousSeam(final int[] seam) {
    for (int i = 1; i < seam.length; ++i) {
      if (Math.abs(seam[i] - seam[i - 1]) > 1) {
        return false;
      }
    }
    return true;
  }


  private void transpose() {
    this.picture = transpose(this.picture);
    this.energy = transpose(this.energy);
    isTransposed = !isTransposed;
  }

  private static Picture transpose(final Picture src) {
    final int srcWidth = src.width(), srcHeight = src.height();
    final Picture target = new Picture(srcHeight, srcWidth);
    for (int h = 0; h < srcHeight; ++h) {
      for (int w = 0; w < srcWidth; ++w) {
        target.set(h, w, src.get(w, h));
      }
    }
    return target;
  }

  private static double[][] transpose(final double[][] src) {
    final int srcWidth = src[0].length, srcHeight = src.length;
    final double[][] target = new double[srcWidth][srcHeight];
    for (int w = 0; w < srcWidth; ++w) {
      for (int h = 0; h < srcHeight; ++h) {
        target[w][h] = src[h][w];
      }
    }
    return target;
  }

  private static final class Point {
    private final int w, h;

    private Point(int w, int h) {
      this.w = w;
      this.h = h;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Point point = (Point) o;
      return w == point.w &&
          h == point.h;
    }

    @Override
    public int hashCode() {
      return Objects.hash(w, h);
    }

    @Override
    public String toString() {
      return "Point{" +
          "w=" + w +
          ", h=" + h +
          '}';
    }
  }

}

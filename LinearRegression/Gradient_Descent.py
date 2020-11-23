import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import math
LEARNING_RATE = 0.1
DEPTH = 2 ** (-32)


def main():
    x1, x2 = 0, 0
    fig = plt.figure()
    plt.clf()
    lx1 = []
    lx2 = []
    J = []
    count = 0
    iteration= []
    """Run until difference of new step and stepsize is < DEPTH"""
    while(True):
        stepSize = 2*(x1 +x2-2)
        newVal = stepSize *LEARNING_RATE
        x1 = x1 - newVal
        x2 = x2 - newVal
        newStep = 2* (x1 + x2 -2)
        if math.fabs(stepSize-newStep) < DEPTH:
            break
        lx1.append(x1)
        lx2.append(x2)
        iteration.append(count)
        count += 1

    """Create a List of J by using the Given Equation"""
    for i in range(0,len(lx1)):
        num = (lx1[i] +lx2[i]-2) ** 2
        J.append(num)
    plt.title("Iteration vs J")
    plt.scatter(iteration,J)
    fig.savefig("IterationVSJ.png")
    fig.clf()

    plt.title("Iteration vs. X1")
    plt.scatter(iteration,lx1)
    fig.savefig("IterationVSX1.png")
    fig.clf()

    plt.title("Iteration vs. X2")
    plt.scatter(iteration, lx2)
    fig.savefig("IterationVSX2.png")
    fig.clf()

main()
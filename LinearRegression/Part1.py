import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

x1 = np.linspace(-4,4,9)
x2 = np.linspace(-6,6,9)
x1,x2 = np.meshgrid(x1,x2)

J = (x1 + x2 - 2) ** 2


fig = plt.figure()
ax = plt.axes(projection='3d')
ax.scatter(x1,x2,J)
fig.savefig("x1vsx2vsJ")
fig.clf()